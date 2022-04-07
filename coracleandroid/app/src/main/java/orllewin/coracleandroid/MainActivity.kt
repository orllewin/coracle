package orllewin.coracleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import examples.basics.HelloCoracleDrawing
import examples.experiments.TadpoleDrawing
import orllewin.coraclelib.AndroidRenderer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coracleTextView = findViewById<AppCompatTextView>(R.id.coracle_print_textview)
        val coracleTextScroll = findViewById<ScrollView>(R.id.print_scroll)

        TadpoleDrawing()
            .renderer(AndroidRenderer(findViewById(R.id.coracle_view), object: AndroidRenderer.Printer{
                override fun print(message: String) {
                    coracleTextView.append("$message\n")
                    coracleTextScroll.post { coracleTextScroll.fullScroll(View.FOCUS_DOWN) }
                }

            }))
            .start()
    }
}