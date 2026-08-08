import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { StudentService } from '../services/api';

export default function Profile() {
    const [student, setStudent] = useState(null); const [error, setError] = useState(''); const navigate = useNavigate();
    useEffect(() => { StudentService.getMyProfile().then(r => setStudent(r.data)).catch(() => setError('Could not load your student profile.')); }, []);
    const logout = () => { localStorage.clear(); navigate('/login'); };
    if (error) return <div className="auth-container"><div className="auth-card glass-panel">{error}</div></div>;
    if (!student) return <div className="auth-container"><div className="auth-card glass-panel">Loading your profile...</div></div>;
    return <div className="dashboard-container animate-fade-in"><div className="dashboard-header"><div><h1>My Student Profile</h1><p style={{ color: 'var(--text-secondary)' }}>Your registered academic details</p></div><button onClick={logout} className="btn-danger">Logout</button></div>
        <div className="glass-panel" style={{ padding: '28px', maxWidth: '760px' }}><h2>{student.firstName} {student.lastName}</h2><p style={{ color: 'var(--text-secondary)' }}>{student.studentId}</p>
        <div className="stats-grid" style={{ marginTop: '25px' }}>
            <div className="stat-card"><h3 className="stat-title">Email</h3><p>{student.email}</p></div><div className="stat-card"><h3 className="stat-title">Phone</h3><p>{student.phone}</p></div>
            <div className="stat-card"><h3 className="stat-title">Department</h3><p>{student.department}</p></div><div className="stat-card"><h3 className="stat-title">Course</h3><p>{student.course}</p></div>
            <div className="stat-card"><h3 className="stat-title">Enrollment Year</h3><p>{student.year}</p></div><div className="stat-card"><h3 className="stat-title">CGPA</h3><p>{student.cgpa}</p></div>
        </div></div></div>;
}
