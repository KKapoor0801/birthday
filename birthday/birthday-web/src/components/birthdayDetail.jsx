import React from 'react';
import { Form, Button } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import FormTextBox from './formTextBox';
import {addBirthdayDetails} from '../actions/BirthdayAction';
import {useDispatch} from 'react-redux';

const BirthdayDetail = () => {
    const [name, setName] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [dob, setDob] = React.useState(null);
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        const formattedDob = formatDate(dob);
        console.log('Form submitted:', { name, email, formattedDob })
        setName('')
        setEmail('')
        setDob(null)
    };

    const formatDate = (dob) => {
      return dob ? `${dob.getDate().toString().padStart(2, '0')}-${(dob.getMonth() + 1).toString().padStart(2, '0')}-${dob.getFullYear()}` : '';
    }

    return (
        <div className="birthday-detail">
        <h3>Birthday Details of the User</h3>
              <Form onSubmit={handleSubmit}>
                <FormTextBox controlId="formName"
                label="Name"
                type="text"
                placeholder="Enter your name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required />

               <FormTextBox controlId="formEmail"
               label="Email"
               type="email"
               placeholder="Enter your email address"
               value={email}
               onChange={(e) => setEmail(e.target.value)}
               required />

                <Form.Group controlId="formDob" className="mb-3 d-flex align-items-center">
                  <Form.Label style={{ minWidth: '95px' }}>Date of Birth</Form.Label>
                  <DatePicker
                    selected={dob}
                    onChange={(date) => setDob(date)}
                    className="form-control"
                    placeholderText="Select date"
                    dateFormat="yyyy-MM-dd"
                    maxDate={new Date()}
                    showYearDropdown
                    scrollableYearDropdown
                    yearDropdownItemNumber={100}
                    required
                  />
                </Form.Group>

                <Button variant="primary" type="submit" onClick={()=>dispatch(addBirthdayDetails({name, email, dob: formatDate(dob)}))}>Submit</Button>
              </Form>
        </div>
    );
}

export default BirthdayDetail;
