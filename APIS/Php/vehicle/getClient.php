<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("../conectiondb.php");
    $licencePlate = $_POST['plate'];


    $query = "SELECT client.*, user.* FROM `vehicle`
              JOIN `client` ON vehicle.dni_client = client.dni_client
              JOIN `user` ON client.dni_client = user.dni_user
              WHERE vehicle.licenceplate = '$licencePlate'";
    $result = $mysql->query($query);

    if ($result->num_rows > 0) {
        $client_info = $result->fetch_assoc();
        http_response_code(200);
        echo json_encode($client_info);
    } else {
        http_response_code(404);
        echo json_encode(array("message" => "Not found client associated with licence plate"));
    }
    $mysql->close();
}