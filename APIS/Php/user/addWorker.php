<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once("../conectiondb.php");

    $name = $_POST['name'];
    $surname = $_POST['surname'];
    $dni = $_POST['dni'];
    $password = $_POST['password'];
    $email = $_POST['email'];
    $age = $_POST['age'];


    $check_query = "SELECT * FROM `user` WHERE `dni_user` = '$dni'";
    $check_result = $mysql->query($check_query);
    if ($check_result->num_rows > 0) {

        try {
            $query_max_worker = "SELECT MAX(numberOfWorker) AS max_worker FROM worker";
            $result_max_worker = $mysql->query($query_max_worker);
            
            if ($result_max_worker && $result_max_worker->num_rows > 0) {
                $row = $result_max_worker->fetch_assoc();
                $max_worker = $row["max_worker"];
                $numberOfWorker = $max_worker + 1;
            } else {
                $numberOfWorker = 1;
            }

            $query_insert = "INSERT INTO `worker` (`dni_worker`, `email`, `numberOfWorker`) VALUES ('$dni', '$email', '$numberOfWorker')";
            $result_insert = $mysql->query($query_insert);
            if ($result_insert === true) {
                $response = array('name' => $name, 'surname' => $surname, 'email' => $email);
                http_response_code(200);
                echo json_encode($response);
            } else {
                http_response_code(500);
                echo json_encode(array("message" => "Error inserting data into 'worker' table."));
            }
        } catch (mysqli_sql_exception $e) {
            http_response_code(500);
            echo json_encode(array("message" => "Database error: " . $e->getMessage()));
        }
    } else {

        try {
            $queryUser = "INSERT INTO `user` (`dni_user`, `name`, `surname`, `password`, `age`) VALUES ('$dni', '$name', '$surname', '$password', '$age')";
            $resulUser = $mysql->query($queryUser);
            
            if ($resulUser === true) {
                $query_max_worker = "SELECT MAX(numberOfWorker) AS max_worker FROM worker";
                $result_max_worker = $mysql->query($query_max_worker);
                
                if ($result_max_worker && $result_max_worker->num_rows > 0) {
                    $row = $result_max_worker->fetch_assoc();
                    $max_worker = $row["max_worker"];
                    $numberOfWorker = $max_worker + 1;
                } else {
                    $numberOfWorker = 1;
                }

                $query_insert = "INSERT INTO `worker` (`dni_worker`, `email`, `numberOfWorker`) VALUES ('$dni', '$email', '$numberOfWorker')";
                $result_insert = $mysql->query($query_insert);
                if ($result_insert === true) {
                    $response = array('name' => $name, 'surname' => $surname, 'email' => $email);
                    http_response_code(200);
                    echo json_encode($response);
                } else {
                    http_response_code(500);
                    echo json_encode(array("message" => "Error inserting data into 'worker' table."));
                }
            } else {
                http_response_code(500);
                echo json_encode(array("message" => "Error inserting data into 'user' table."));
            }
        } catch (mysqli_sql_exception $e) {
            http_response_code(500);
            echo json_encode(array("message" => "Database error: " . $e->getMessage()));
        }
    }


    $mysql->close();
}
?>
