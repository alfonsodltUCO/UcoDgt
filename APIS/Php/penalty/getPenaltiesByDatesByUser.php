<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    if(isset($_POST['date1']) && isset($_POST['date2']) && isset($_POST['dni'])){
        $start_date = $_POST['date1'];
        $end_date = $_POST['date2'];
        $dni = $_POST['dni'];

        $query = "SELECT * FROM `penalty` WHERE `date` BETWEEN '$start_date' AND '$end_date' AND `dni_client` = '$dni'";
        
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
            echo json_encode(array("message" => "Penalties not found for the given dates and DNI"));
        }
    }else{
        http_response_code(400);
        echo json_encode(array("message" => "Missing start date, end date, or DNI parameters"));
    }
    $mysql->close();
}