<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $name = $_POST['name'];
    $dateLicenceObtaining = $_POST['dateObtaining'];
    $surname = $_POST['surname'];
    $dni =  $_POST['dni'];
    $licencePoints = $_POST['licencePoints'];
    $password = $_POST['password'];
    $email = $_POST['email'];
    $age = $_POST['age'];
    $dateLastUpdate= $_POST['dateLastUpdate'];

    try {

        $check_query = "SELECT * FROM `user` WHERE `dni_user` = '$dni'";
        $check_result = $mysql->query($check_query);
        if($check_result->num_rows > 0) {

            $query = "INSERT INTO `client` (`dni_client`, `email`, `licencepoints`, `dateLicenceObtaining`, `dateLastUpdate`) VALUES ('$dni', '$email', '$licencePoints', '$dateLicenceObtaining', '$dateLastUpdate')";
            $result = $mysql->query($query);
            if($result === true) {
                $response = array('name' => $name, 'surname' => $surname, 'email' => $email);
                http_response_code(200);
                echo json_encode($response);
            } else {
                http_response_code(500);
                echo "Error inserting data into 'client' table.";
            }
        } else {
            $queryUser = "INSERT INTO `user` (`dni_user`, `name`, `surname`, `password`, `age`) VALUES ('$dni', '$name', '$surname', '$password', '$age')";
            $resultUser = $mysql->query($queryUser);
            if($resultUser === true) {
                $queryClient = "INSERT INTO `client` (`dni_client`, `email`, `licencepoints`, `dateLicenceObtaining`, `dateLastUpdate`) VALUES ('$dni', '$email', '$licencePoints', '$dateLicenceObtaining', '$dateLastUpdate')";
                $resultClient = $mysql->query($queryClient);
                if($resultClient === true) {
                    $response = array('name' => $name, 'surname' => $surname, 'email' => $email);
                    http_response_code(200);
                    echo json_encode($response);
                } else {
                    http_response_code(500);
                    echo "Error inserting data into 'client' table.";
                }
            } else {
                http_response_code(500);
                echo "Error inserting data into 'user' table.";
            }
        }
    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        echo "Error: " . $e->getMessage();
    }

    $mysql->close();
}