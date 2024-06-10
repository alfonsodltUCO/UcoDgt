<?php
    require_once("../conectiondb.php");

if(isset($_POST['dni'])) {
    $dni = $_POST['dni'];

    $query = "SELECT * FROM `penalty` WHERE `dni_client` = '$dni' AND `points` > 0 ORDER BY `date` DESC LIMIT 1";

    $result = $mysql->query($query);

    if($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        
        echo json_encode($row);
    } else {
        http_response_code(404);
        echo json_encode(array("message" => "Not found penalty with point loss for use"));
    }

    $mysql->close();
} else {
    $response = array(
        'message' => 'No se proporcionó un DNI por POST'
    );
    echo json_encode($response);
}
