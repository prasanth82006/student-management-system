import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthService } from '../services/api';

const Register = () => {
    const [userData, setUserData] = useState({ firstname: '', lastname: '', email: '', password: '', phone: '', department: '', course: '', year: '', cgpa: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const handleChange = (e) => setUserData({ ...userData, [e.target.name]: e.target.value });

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const res = await AuthService.register({ ...userData, year: Number(userData.year), cgpa: Number(userData.cgpa) });
            localStorage.setItem('token', res.data.token);
            localStorage.setItem('role', res.data.role);
            navigate('/profile');
        } catch (err) { setError(err.response?.data?.details || err.response?.data?.message || 'Registration failed'); }
    };
    const fields = [
        ['firstname', 'First Name', 'text'], ['lastname', 'Last Name', 'text'], ['email', 'Email Address', 'email'], ['phone', 'Phone Number', 'tel'],
        ['department', 'Department', 'text'], ['course', 'Course', 'text'], ['year', 'Enrollment Year', 'number'], ['cgpa', 'CGPA', 'number'], ['password', 'Password (minimum 6 characters)', 'password']
    ];
    return <div className="auth-container"><div className="auth-card glass-panel animate-fade-in" style={{ maxWidth: '720px' }}>
        <h2 style={{ textAlign: 'center', marginBottom: '10px', fontSize: '2rem' }}>Student Registration</h2>
        <p style={{ textAlign: 'center', color: 'var(--text-secondary)', marginBottom: '25px' }}>Create your student record and portal account together.</p>
        {error && <div className="error-banner">{error}</div>}
        <form onSubmit={handleRegister}><div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
            {fields.map(([name, label, type]) => <div className="input-group" key={name}><label className="input-label">{label}</label><input type={type} name={name} step={name === 'cgpa' ? '0.1' : undefined} min={name === 'cgpa' ? '0' : undefined} className="input-field" value={userData[name]} onChange={handleChange} required /></div>)}
        </div><button type="submit" className="btn-primary" style={{ marginTop: '10px' }}>Register as Student</button>
        <p style={{ textAlign: 'center', marginTop: '20px', color: 'var(--text-secondary)' }}>Already have an account? <a href="/login" style={{ color: 'var(--primary-accent)' }}>Login here</a></p></form>
    </div></div>;
};
export default Register;
