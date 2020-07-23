package com.example.vinicius.fixmycity.Cenario_Cadastro

import android.Manifest
import android.annotation.TargetApi
import android.graphics.drawable.BitmapDrawable
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.vinicius.fixmycity.BuildConfig
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.R
import com.example.vinicius.fixmycity.Utils.GlideApp
import kotlinx.android.synthetic.main.activity_cadastro_dificuldades.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class Cadastro_Dificuldades : AppCompatActivity(), Cadastro_Contract.view {



    lateinit var opcao : Spinner

    val presenter: Cadastro_Contract.presenter = Cadastro_Presenter(this)
    var idUser = 0
    var caminhoFoto: String? = null
    var caminhoFotoVerificado: String? = null



    companion object {
        private const val REQUEST_CAMERA: Int = 5
        private const val REQUEST_GALLERY: Int = 7
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_dificuldades)

        presenter.getIdUser(this)
        opcao = findViewById(R.id.spinnerCategorias) as Spinner
        var categoria_escolhida: Int = 0

        val edita_dificuldade : Dificuldades? = intent.getSerializableExtra("edita") as Dificuldades?
        if(edita_dificuldade!=null){
            desc.setText(edita_dificuldade.descricao)
            Adicionar.text = (getString(R.string.editar))

            val caminho = "https://fixmycitymobile.000webhostapp.com/fotos/" +  edita_dificuldade.dataTimestamp + ".jpg"
            GlideApp.with(this)
                .load(caminho)
                .into(image)

        }
        val categorias = arrayOf("Selecione uma categoria","Árvore caída", "Poste sem luz", "Lixeira destruída", "Outro")
        opcao.adapter = ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, categorias)

        opcao.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoria_escolhida = position
            }
        }

        Adicionar.setOnClickListener {

            if(categoria_escolhida == 0){
                opcao.requestFocus()
                Toast.makeText(this, "Escolha uma categoria válida", Toast.LENGTH_LONG).show()
            }
            else if(edita_dificuldade != null){
                CriaObjeto(categorias, categoria_escolhida, edita_dificuldade)
            }
            else if(caminhoFotoVerificado.isNullOrBlank()){
                Toast.makeText(this, "Adicione uma foto", Toast.LENGTH_LONG).show()
            }
            else{
                CriaObjeto(categorias, categoria_escolhida, null)
            }

        }

        tirarFotoBTN.setOnClickListener {
            if (checkCallingOrSelfPermission(android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Permissão para acessar a câmera não aceita", Toast.LENGTH_LONG).show()
            }else
                TirarFoto()

        }

        selecionarFoto.setOnClickListener {
            if (checkCallingOrSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Permissão para acessar arquivos não aceita", Toast.LENGTH_LONG).show()
            }else {
                PegarFotoGaleria()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun PegarFotoGaleria() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val pickImageIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickImageIntent, REQUEST_GALLERY)
        }
    }

    override fun cadastrado(){
        Toast.makeText(this,"Dificuldade salva com sucesso", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun CriaObjeto(categorias: Array<String>, categoria_escolhida: Int, edita_dificuldade : Dificuldades?) {

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var data = Date()
        val currentDate = sdf.format(data)
        val timestamp = data.time
        val dificuldade :Dificuldades?
        if(edita_dificuldade!=null){
            data = sdf.parse(edita_dificuldade.data) as Date
            dificuldade = Dificuldades(
                descricao = desc.text.toString(),
                categoria = categorias[categoria_escolhida],
                data = edita_dificuldade.data,
                dataTimestamp = data.time.toString(),
                localizacao = edita_dificuldade.localizacao,
                locX = edita_dificuldade.locX,
                locY = edita_dificuldade.locY,
                ID = edita_dificuldade.ID,
                userID = idUser
            )

            presenter.onEditaDificuldade(this, dificuldade)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            presenter.uploadImage(bitmap, data.time.toString())

        }
        else{
            dificuldade = Dificuldades(
                descricao = desc.text.toString(),
                categoria = categorias[categoria_escolhida],
                data = currentDate.toString(),
                dataTimestamp = timestamp.toString(),
                localizacao = intent.getStringExtra(Cadastra_Localizacao.ENDERECO) as String,
                locX = intent.getDoubleExtra(Cadastra_Localizacao.LOCX, 0.0),
                locY = intent.getDoubleExtra(Cadastra_Localizacao.LOCY, 0.0),
                userID = idUser
            )

            presenter.onSalvaDificuldade(this, dificuldade)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            presenter.uploadImage(bitmap, timestamp.toString())

        }
        cadastrado()
    }

    private fun TirarFoto(){

        val tirarFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (tirarFoto.resolveActivity(packageManager) != null) {

            val arquivoFoto = MontaArquivoFoto()
            val URIFoto = FileProvider.getUriForFile(this,"${BuildConfig.APPLICATION_ID}.fileprovider", arquivoFoto)
            tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, URIFoto)

            startActivityForResult(tirarFoto, REQUEST_CAMERA)

        }else
            Toast.makeText(this, "Impossível tirar foto", Toast.LENGTH_LONG).show()
    }

    private fun MontaArquivoFoto():File{
        val nomeArquivo = System.currentTimeMillis().toString()
        val diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val arquivoFoto = File.createTempFile(nomeArquivo,".jpg",diretorio)

        caminhoFoto = arquivoFoto.absolutePath

        return arquivoFoto
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){

            val arquivoFoto = File(caminhoFoto)
            if(arquivoFoto.exists()){
                caminhoFotoVerificado = caminhoFoto

                GlideApp.with(this)
                    .load(caminhoFotoVerificado)
                    .into(image)
                image.visibility = View.VISIBLE
            }
        }
        if(requestCode == REQUEST_GALLERY){
            val uri = data?.data
            caminhoFotoVerificado = uri.toString()
            GlideApp.with(this)
                .load(caminhoFotoVerificado)
                .into(image)
            image.visibility = View.VISIBLE
        }
    }

    override fun getIDUser(id : Int): Int {
        idUser = id
        return id
    }

}
