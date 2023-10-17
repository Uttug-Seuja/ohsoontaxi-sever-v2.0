INSERT INTO profile_image(profile_image_id, image_url)
VALUES
    (1, 'https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7C098f3311-eae2-4f51-8ae7-90b4fa0887fc.jpeg'),
    (2, 'https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cea98a33e-8cda-49e9-ae0a-5ec66f935cea.jpeg'),
    (3, 'https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Ceedc049d-b84e-427b-abb2-003f454af16d.jpeg'),
    (4, 'https://ohsoontaxi.s3.ap-northeast-2.amazonaws.com/1%7Cf6e4f790-665e-42c1-a013-0185f31672e0.jpeg')
ON DUPLICATE KEY UPDATE image_url = VALUES(image_url);