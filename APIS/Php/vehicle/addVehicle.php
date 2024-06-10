<?php
   if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $licencePlate = $_POST['licencePlate'];
    $carType = $_POST['carType'];
    $color = $_POST['color'];
    $validItvFrom = $_POST['validItvFrom'];
    $validItvTo = $_POST['validItvTo'];
    $dni_client = $_POST['dni_client'];

    try {
        $check_query = "SELECT * FROM `vehicle` WHERE `licenceplate` = '$licencePlate'";
        $check_result = $mysql->query($check_query);
        if ($check_result->num_rows > 0) {
            http_response_code(400);
            echo "Error: Vehicle already exists.";
            exit;
        }


        $query = "INSERT INTO `vehicle` (`licenceplate`, `carType`, `color`, `validItvFrom`, `validItvTo`, `dni_client`) 
                  VALUES ('$licencePlate', '$carType', '$color', '$validItvFrom', '$validItvTo', '$dni_client')";
        $result = $mysql->query($query);
        if($result === true) {
            $response = array('licencePlate' => $licencePlate, 'carType' => $carType, 'color' => $color);
            http_response_code(200);
            echo json_encode($response);
        } else {
            http_response_code(500);
            echo "Error: " . $mysql->error;
        }

    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        echo "Error: " . $e->getMessage();
    }
    
    $mysql->close();
}      
