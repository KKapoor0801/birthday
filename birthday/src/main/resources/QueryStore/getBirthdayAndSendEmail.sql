SELECT
       ID as id,
       NAME AS name,
       BDAY_DT AS dateOfBirth,
       CRE_DT as createdDate,
       EMAIL_ADDR AS emailAddress
FROM KESHAV_USER.BDAY_DTLS
WHERE TO_CHAR(BDAY_DT, 'DD-MM') = :dayOfMonth