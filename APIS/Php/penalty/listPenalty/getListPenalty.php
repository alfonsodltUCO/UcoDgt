<?php 
    if($_SERVER['REQUEST_METHOD']=='GET'){
        require_once("../../conectiondb.php");
        $reason=$_GET['reason'];

        
        $query = "SELECT * FROM `listpenalty` WHERE `reason` = '$reason'";
        $result = $mysql->query($query);

        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $array = $row;
            }
            http_response_code(200);

            echo json_encode($array);
        }else{
            http_response_code(404);
            echo json_encode("error not found");
        }
        $result->close();
        $mysql->close();
    }
