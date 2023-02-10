package id.yuana.compose.movieapp.data.remote

import java.io.InputStreamReader

class MockResponseFileReader(private val path: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}