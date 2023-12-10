package com.example.shoplistv2

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage

class HelloApplication : Application() {
    /*override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }*/
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("shop-list-view.fxml"))
        val root: Parent = fxmlLoader.load()


        val controller = fxmlLoader.getController<ShopListController>()
        controller.setStage(stage)  // передача Stage в контроллер

        stage.title = "Список товаров"
        val scene = Scene(root, 500.0, 400.0)
        stage.scene = scene

        stage.setOnCloseRequest { event ->
            event.consume() // Отменяем закрытие окна, чтобы показать Alert

            val alert = Alert(Alert.AlertType.CONFIRMATION)
            alert.title = "Подтверждение"
            alert.headerText = "Вы уверены, что хотите выйти?"
            alert.contentText = "Все несохраненные изменения будут потеряны."

            val result = alert.showAndWait()
            if (result.isPresent && result.get() == ButtonType.OK) {
                // Если пользователь согласен закрыть, закрываем окно
                stage.close()
            }
            // В противном случае, продолжаем отображение окна
        }

        stage.show()

    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}