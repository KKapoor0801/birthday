import { combineReducers } from "redux";
import { birthdayReducer } from './birthdayReducer';

export default combineReducers ({ birthday: birthdayReducer });