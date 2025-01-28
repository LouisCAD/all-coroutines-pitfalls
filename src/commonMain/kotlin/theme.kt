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
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = MaterialTheme.typography.run {
            copy(
                displayLarge = displayLarge.copy(fontFamily = avenirNext, fontWeight = FontWeight.SemiBold),
                displayMedium = displayMedium,
                displaySmall = displaySmall,
                headlineLarge = headlineLarge,
                headlineMedium = headlineMedium,
                headlineSmall = headlineSmall,
                titleLarge = titleLarge,
                titleMedium = titleMedium,
                titleSmall = titleSmall,
                bodyLarge = bodyLarge,
                bodyMedium = bodyMedium,
                bodySmall = bodySmall,
                labelLarge = labelLarge,
                labelMedium = labelMedium,
                labelSmall = labelSmall,
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
    Font(Res.font.avenir_next_b, FontWeight.Bold),
    Font(Res.font.avenir_next_b_i, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.avenir_next_db, FontWeight.SemiBold),
    Font(Res.font.avenir_next_db_i, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.avenir_next_heavy, FontWeight.Black),
    Font(Res.font.avenir_next_heavy_i, FontWeight.Black, FontStyle.Italic),
)
