<?php

//token = FirebaseInstanceId.getInstance().getToken();
//$token=$_POST['token'];
//$title=$_POST['title'];
//$body=$_POST['body'];
function sendnote($msg){
    $token='dIXiMX_RQ36Y7pEvk9U2c4:APA91bFq5sOMbrMdzWDOxW6UI1UXU-0w7PZIxEyF3Q0KtpZZbi8KbOe_5GqXIgQCjS5Jz8sh2ROTQpQI6ypbWlEEpkYw5Hl5_eYyOufX-fWchhNQoRhSJIT9YL80z9MJZBxAnEdS6D_c';
    $title='Notification';
    $body= $msg;
    define('API_ACCESS_KEY','AAAAm8IjkMQ:APA91bGS1gIV1v9kEZg45I7IYB8WBCZG_JR_5ozUOq42_TMrTMAb42x4rPCJ9vnfHatdqmjJ1S6iHwRGm8D1kErQLycgiN9FPB3aCSwKNmwD59kdYh5Mi-ifUSK9O6eAD5Wodl803UuA');
    $fcmUrl = 'https://fcm.googleapis.com/fcm/send';
    //$token='ePAMYu6vRsKZDhHTKYfuFI:APA91bGDPhTHkgw8vWvaALWq08GVgr-X2ef8kIrH007lC1b--3SGcbu4x22zzGMmj-SkuKwIGrGYF2_b95Sxv09T8TyDi7GnXHl3mofgDdEUx_TlEyPIfbvkB3qRvupL4v_BRIPXkNCG';

    $notification = [
        'title' =>$title,
        'body' => $body,
        'icon' =>'myIcon', 
        'sound' => 'mySound'
    ];
    $extraNotificationData = ["message" => $notification,"moredata" =>'dd'];

    $fcmNotification = [
        //'registration_ids' => $tokenList, //multple token array
        'to'        => $token, //single token
        'notification' => $notification,
        'data' => $extraNotificationData
    ];

    $headers = [
        'Authorization: key=' . API_ACCESS_KEY,
        'Content-Type: application/json'
    ];


    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL,$fcmUrl);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fcmNotification));
    $result = curl_exec($ch);
    curl_close($ch);
}

        //echo $result;