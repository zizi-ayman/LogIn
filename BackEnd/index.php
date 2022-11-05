<?php
    require_once 'user.php';
    $username = "";
    $password = "";
    if(isset($_POST['username'])){
        $username = $_POST['username'];
    }
    if(isset($_POST['password'])){
        $password = $_POST['password'];
    }
    if(isset($_POST['email'])){
        $email = $_POST['email'];
    }
    $userObject = new User();
       // Registration
    if(!empty($username) && !empty($password) && !empty($email)){    
        $hashed_password = md5($password);
        $json_registration = $userObject->createNewRegisterUser($username, $password, $email);
        echo json_encode($json_registration);
    }
    // Login
    if(!empty($username) && !empty($password)){
        $json_array = $userObject->loginUsers($username, $password);
        echo json_encode($json_array);
    }   
?>