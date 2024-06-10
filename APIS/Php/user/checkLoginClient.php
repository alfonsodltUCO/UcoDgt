<?php
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");
    $email=$_GET['email'];

    
    $query = "SELECT dni_client FROM `client` WHERE `email` = '$email'";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $row = $result->fetch_assoc();
        $dni = $row['dni_client'];

        $query_client_user = "SELECT client.*, user.* FROM `client` 
                             JOIN `user` ON client.dni_client = user.dni_user
                             WHERE client.dni_client = '$dni'";
        $result_client_user = $mysql->query($query_client_user);

        if($result_client_user->num_rows > 0){
            $array = array();
            while($row_client_user = $result_client_user->fetch_assoc()){
                $array[] = $row_client_user;
            }
            http_response_code(200);
            echo json_encode($array[0]);
        } else {
            http_response_code(404);
            echo json_encode("error not found in client_user table");
        }
    } else {
        http_response_code(404);
        echo json_encode("error not found in client table");
    }
    
    $result->close();
    $mysql->close();
}
