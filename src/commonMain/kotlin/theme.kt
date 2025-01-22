import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import net.kodein.cup.widgets.material3.cupScaleDown

@Composable
fun PresentationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = MaterialTheme.typography.cupScaleDown()
    ) {
        content()
    }
}
