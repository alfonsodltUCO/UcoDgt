<?php 
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");

    $query = "SELECT user.*, worker.* FROM `user`
              JOIN `worker` ON user.dni_user = worker.dni_worker";
    $result = $mysql->query($query);

    if($result->num_rows > 0){
        $array = array();
        while($row = $result->fetch_assoc()){
            $array[] = $row;
        }
        http_response_code(200);
        $JsonToSend = array("workers" => $array);
        echo json_encode($JsonToSend);
    }else{
        http_response_code(404);
        echo json_encode(array("message" => "Workers not found"));
    }
    $mysql->close();
}