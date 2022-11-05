<?php
    class DbConnect{
        private $connect;
        public function __construct(){
            $this->connect = mysqli_connect("localhost","root","","login-db");
            if (mysqli_connect_errno()){
                echo "Unable to connect to MySQL Database: " . mysqli_connect_error();
            }
        }
        public function getDb(){
            return $this->connect;
        }
    }
?>