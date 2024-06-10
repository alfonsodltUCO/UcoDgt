<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    require_once("../conectiondb.php");

    $dni = $_POST['dni'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    try {
        // Flag to track if any update has been performed
        $updatePerformed = false;

        // Update password if provided
        if (!empty($password)){
	    echo "hola";
            $update_user_query = "UPDATE `user` SET `password`='$password' WHERE `dni_user`='$dni'";
            $update_user_result = $mysql->query($update_user_query);

            if ($update_user_result === true) {
                $updatePerformed = true;
            } else {
                http_response_code(500);
                echo "An error occurred while updating user password.";
                exit;
            }
        }

        // Update email if provided
        if (!empty($email)) {
            $update_client_query = "UPDATE `worker` SET `email`='$email' WHERE `dni_worker`='$dni'";
            $update_client_result = $mysql->query($update_client_query);

            if ($update_client_result === true) {
                $updatePerformed = true;
            } else {
                http_response_code(500);
                echo "An error occurred while updating worker email.";
                exit;
            }
        }

        // Check if any update was performed
        if ($updatePerformed) {
            $response = array('dni' => $dni, 'email' => $email);
            http_response_code(200);
            echo json_encode($response);
        } else {
            http_response_code(400);
            echo "No updates were performed. Please provide either a password or an email to update.";
        }
    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        echo "Error: " . $e->getMessage();
    }

    $mysql->close();
}
?>
