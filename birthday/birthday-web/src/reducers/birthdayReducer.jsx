import { ADD_BIRTHDAY_DETAILS_API_CALL_DONE_BY_SAGA } from '../constants/birthdayConstants';
const initialData = [];

export const birthdayReducer = (state = initialData, action) => {
    switch (action.type) {
        case ADD_BIRTHDAY_DETAILS_API_CALL_DONE_BY_SAGA:
            console.log('Reducer called and Action received:', action);
            return [action.data];
        default:
            return state;
    }
}