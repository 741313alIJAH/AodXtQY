// 代码生成时间: 2025-09-23 05:22:23
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

@ApplicationScoped
@Singleton
public class ThemeSwitcher {
    // Singleton instance of the theme
    private static Theme currentTheme = Theme.LIGHT;

    // Enum to represent different themes
    public enum Theme {
        LIGHT, DARK
    }

    // Method to switch the theme
    public synchronized void switchTheme() {
        currentTheme = currentTheme == Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
    }

    // Method to get the current theme
    public synchronized Theme getCurrentTheme() {
        return currentTheme;
    }

    // Method to set the theme programmatically
    public synchronized void setTheme(Theme theme) {
        if (theme == null || !Theme.values().contains(theme)) {
            throw new IllegalArgumentException("Invalid theme: " + theme);
        }
        currentTheme = theme;
    }

    // Event subscriber to handle theme changes
    public void onThemeChange(@Observes Theme theme) {
        currentTheme = theme;
    }

    // Producer method to provide the current theme to other beans
    @Produces
    public Theme produceCurrentTheme() {
        return getCurrentTheme();
    }
}
