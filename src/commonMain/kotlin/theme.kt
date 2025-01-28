import all_coroutines_pitfalls.generated.resources.*
import all_coroutines_pitfalls.generated.resources.Res
import all_coroutines_pitfalls.generated.resources.avenir_next_b
import all_coroutines_pitfalls.generated.resources.avenir_next_b_i
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import net.kodein.cup.widgets.material3.cupScaleDown
import org.jetbrains.compose.resources.Font

@Composable
fun PresentationTheme(content: @Composable () -> Unit) {
    val avenirNext = AvenirNext()
    val helveticaNeue = AvenirNext()
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = MaterialTheme.typography.run {
            copy(
                displayLarge = displayLarge.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                displayMedium = displayMedium.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                displaySmall = displaySmall.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                headlineLarge = headlineLarge.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                headlineMedium = headlineMedium.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                headlineSmall = headlineSmall.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                titleLarge = titleLarge.copy(fontFamily = avenirNext, fontWeight = FontWeight.Medium),
                titleMedium = titleMedium.copy(fontFamily = avenirNext, fontWeight = FontWeight.Medium),
                titleSmall = titleSmall.copy(fontFamily = avenirNext, fontWeight = FontWeight.Medium),
                bodyLarge = bodyLarge.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Light),
                bodyMedium = bodyMedium.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Light),
                bodySmall = bodySmall.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Light),
                labelLarge = labelLarge.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Medium),
                labelMedium = labelMedium.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Medium),
                labelSmall = labelSmall.copy(fontFamily = helveticaNeue, fontWeight = FontWeight.Medium),
            )
        }.cupScaleDown()
    ) {
        content()
    }
}

@Composable
private fun AvenirNext() = FontFamily(
    Font(Res.font.avenir_next_ul, FontWeight.ExtraLight),
    Font(Res.font.avenir_next_ul_i, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.avenir_next_r, FontWeight.Normal),
    Font(Res.font.avenir_next_r_i, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.avenir_next_db, FontWeight.SemiBold),
    Font(Res.font.avenir_next_db_i, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.avenir_next_b, FontWeight.Bold),
    Font(Res.font.avenir_next_b_i, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.avenir_next_heavy, FontWeight.Black),
    Font(Res.font.avenir_next_heavy_i, FontWeight.Black, FontStyle.Italic),
)

@Composable
private fun HelveticaNeue() = FontFamily(
    Font(Res.font.helvetica_neue_ul, FontWeight.ExtraLight),
    Font(Res.font.helvetica_neue_ul_i, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.helvetica_neue_thin, FontWeight.Thin),
    Font(Res.font.helvetica_neue_thin_i, FontWeight.Thin, FontStyle.Italic),
    Font(Res.font.helvetica_neue_l, FontWeight.Light),
    Font(Res.font.helvetica_neue_l_i, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.helvetica_neue_r, FontWeight.Normal),
    Font(Res.font.helvetica_neue_r_i, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.helvetica_neue_m, FontWeight.Medium),
    Font(Res.font.helvetica_neue_m_i, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.helvetica_neue_bold, FontWeight.Bold),
    Font(Res.font.helvetica_neue_bold_i, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.helvetica_neue_c_bold, FontWeight.ExtraBold),
    Font(Res.font.helvetica_neue_c_bold, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.helvetica_neue_c_black, FontWeight.Black),
)
