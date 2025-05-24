select name as name,
       bday_dt as dateOfBirth,
       email_addr as emailAddress
from keshav_user.bday_dtls
where trunc(bday_dt) = to_date(:birthdayDate, 'DD-MM-YY')