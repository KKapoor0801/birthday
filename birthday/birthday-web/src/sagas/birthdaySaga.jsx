import { takeEvery, put } from 'redux-saga/effects';
import { ADD_BIRTHDAY_DETAILS, ADD_BIRTHDAY_DETAILS_API_CALL_DONE_BY_SAGA } from '../constants/birthdayConstants';

function* insertBirthday(action) {
    try {
        // Extract data from action.payload
        const { name, email, dob } = action.payload;
        const response = yield fetch('http://localhost:8081/api/v1/birthday/insertBirthday', {
            method: 'POST',
            headers: { 'traceId': 'trace-123', 'Content-Type': 'application/json' },
            body: JSON.stringify({
                name: name,
                emailAddress: email,
                dateOfBirth: dob
            })
        });
        const data = yield response.json();
        console.log('Saga called and response received:', data);
        yield put(
            {
                type: ADD_BIRTHDAY_DETAILS_API_CALL_DONE_BY_SAGA,
                data: data
            }
        );
    } catch (error) {
        console.error('Saga error:', error);
    }
}


function* birthdaySaga() { //this is a generator function and it used to handle asynchronous actions in Redux-Saga
    yield takeEvery(ADD_BIRTHDAY_DETAILS, insertBirthday);
}

export default birthdaySaga;  