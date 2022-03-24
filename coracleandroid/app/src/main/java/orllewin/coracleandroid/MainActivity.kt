package orllewin.coracleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import examples.basics.HelloCoracleDrawing
import examples.vectors.TadpoleDrawing
import orllewin.coraclelib.AndroidRenderer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TadpoleDrawing()
            .renderer(AndroidRenderer(findViewById(R.id.coracle_view)))
            .start()
    }
}