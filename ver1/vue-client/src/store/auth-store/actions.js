import axios from 'axios'
import router from '../../router/index'

export const actions = {
    // loginAndSaveToken(formData) {
    //     axios.post('https://fake-url.com/api/auth', formData)
    //     .then((response) => {
    //         localStorage.setItem('auth_token', response.datatoken);
    //         router.push('/profile');
    //     }).catch((error) => {
    //         //will always redirect to profile 
    //         router.push('/profile');
    //     });
    // },
    loginAndSaveToken(formData) {
        axios.post('https://fake-url.com/api/auth', formData)
        .then((response) => {
            localStorage.setItem('auth_token', response.datatoken);
            router.push('/profile');
        }).catch((error) => {
            //will always redirect to profile 
            router.push('/profile');
        });
    },
    signup(formData) {
        axios.post('http://127.0.0.1:8000/accounts/signup/', formData)
        .then((response) => {
            console.log(response)
            router.push('/');
        }).catch((error) => {
            console.log(error)
            router.push('/');
        });
    },
};