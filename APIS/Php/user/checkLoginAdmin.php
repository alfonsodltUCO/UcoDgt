<?php
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");
    $email=$_GET['email'];

    
    $query = "SELECT dni FROM `admin` WHERE `email` = '$email'";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $row = $result->fetch_assoc();
        $dni = $row['dni'];

        $query_admin_user = "SELECT admin.*, user.* FROM `admin` 
                             JOIN `user` ON admin.dni = user.dni_user
                             WHERE admin.dni = '$dni'";
        $result_admin_user = $mysql->query($query_admin_user);

        if($result_admin_user->num_rows > 0){
            $array = array();
            while($row_admin_user = $result_admin_user->fetch_assoc()){
                $array[] = $row_admin_user;
            }
            http_response_code(200);
            echo json_encode($array[0]);
        } else {
            http_response_code(404);
            echo json_encode("error not found in admin_user table");
        }
    } else {
        http_response_code(404);
        echo json_encode("error not found in admin table");
    }
    
    $result->close();
    $mysql->close();
}