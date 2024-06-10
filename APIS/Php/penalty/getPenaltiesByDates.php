<?php
if($_SERVER['REQUEST_METHOD']=='GET'){
    require_once("../conectiondb.php");

    if(isset($_GET['start']) && isset($_GET['end'])){
        $start_date = $_GET['start'];
        $end_date = $_GET['end'];

        $query = "SELECT * FROM `penalty` WHERE `date` BETWEEN '$start_date' AND '$end_date'";
        
        $result = $mysql->query($query);

        if($result->num_rows > 0){
            $array = array();
            while($row = $result->fetch_assoc()){
                $array[] = $row;
            }
            http_response_code(200);
            $JsonToSend = array("penalties" => $array);
            echo json_encode($JsonToSend);
        }else{
            http_response_code(404);
            echo json_encode(array("message" => "Penalties not found between the given dates"));
        }
    }else{
        http_response_code(400);
        echo json_encode(array("message" => "Missing start or end date parameters"));
    }
    $mysql->close();
}

