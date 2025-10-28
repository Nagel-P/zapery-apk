plugins {
    // Apenas plugins globais, sem aplicar Android aqui
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.android.application") version "8.3.0" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
