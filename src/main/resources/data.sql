INSERT INTO profile_image(profile_image_id, image_url)
VALUES
    (1, 'https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C107648f1-9d4a-4f4b-bec6-69a7e911af9a.jpeg'),
    (2, 'https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C1eec6834-8c60-464e-8c02-ae5d8116ba85.jpeg'),
    (3, 'https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C46f4fcf7-744a-457c-8f64-7b7e159a49cd.jpeg'),
    (4, 'https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7C9f644f39-1929-48fb-bee8-6319c95a53bc.jpeg'),
    (5, 'https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7Cd782da88-cc95-42b5-8233-416891e875a2.jpeg')
ON DUPLICATE KEY UPDATE image_url = VALUES(image_url);