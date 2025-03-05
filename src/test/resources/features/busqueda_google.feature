Feature: Búsqueda en Google

  Scenario: Buscar el término "automatización" y acceder a Wikipedia
    Given que el usuario abre el navegador y navega a Google
    When el usuario ingresa "automatización" en la barra de búsqueda
    And hace clic en el botón de búsqueda
    Then el usuario hace clic en el enlace de Wikipedia
    And extrae el primer año mencionado en la página
    And toma una captura de pantalla de la página de Wikipedia
