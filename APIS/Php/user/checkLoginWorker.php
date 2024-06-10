<?php 
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");
    $email=$_GET['email'];

    
    $query = "SELECT dni_worker FROM `worker` WHERE `email` = '$email'";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $row = $result->fetch_assoc();
        $dni = $row['dni_worker'];

        $query_worker_user = "SELECT worker.*, user.* FROM `worker` 
                             JOIN `user` ON worker.dni_worker = user.dni_user
                             WHERE worker.dni_worker = '$dni'";
        $result_worker_user = $mysql->query($query_worker_user);

        if($result_worker_user->num_rows > 0){
            $array = array();
            while($row_worker_user = $result_worker_user->fetch_assoc()){
                $array[] = $row_worker_user;
            }
            http_response_code(200);
            echo json_encode($array[0]);
        } else {
            http_response_code(404);
            echo json_encode("error not found in worker_user table");
        }
    } else {
        http_response_code(404);
        echo json_encode("error not found in worker table");
    }
    
    $result->close();
    $mysql->close();
}