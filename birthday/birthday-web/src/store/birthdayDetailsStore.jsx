import rootReducer from '../reducers/rootReducer';
import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import birthdaySaga from '../sagas/birthdaySaga';

const sagaMiddleWare = createSagaMiddleware();

const store = configureStore({
    reducer: rootReducer,
    middleware: () => [sagaMiddleWare]
    
}
);

sagaMiddleWare.run(birthdaySaga);

export default store;