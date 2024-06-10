<?php 
    if($_SERVER['REQUEST_METHOD']=='GET'){
        require_once("../conectiondb.php");
        
        $query = "SELECT * FROM `penalty`";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array[] = $row;
            }
            http_response_code(200);
            $JsonToSend=array("penalties" => $array);
            echo json_encode($JsonToSend);
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Penalies not found"));
        }
        $mysql->close();
    }