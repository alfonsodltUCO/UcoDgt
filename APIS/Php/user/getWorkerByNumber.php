<?php
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    require_once("../conectiondb.php");
    $number_of_worker = $_GET['number'];

    $query = "SELECT worker.*, user.* FROM `worker`
              JOIN `user` ON worker.dni_worker = user.dni_user
              WHERE worker.numberOfWorker = '$number_of_worker'";
    $result = $mysql->query($query);

    if ($result->num_rows > 0) {
        $array = $result->fetch_assoc();
        http_response_code(200);
        echo json_encode($array);
    } else {
        http_response_code(404);
        echo json_encode(array("message" => "Worker not found"));
    }
    $mysql->close();
}