import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { DashboardService } from '../services/api';

const Dashboard = () => {
    const [stats, setStats] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const res = await DashboardService.getStats();
                setStats(res.data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchStats();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        navigate('/login');
    };

    return (
        <div className="dashboard-container animate-fade-in">
            <div className="dashboard-header">
                <h1 style={{ fontSize: '2.5rem' }}>Overview</h1>
                <div style={{ display: 'flex', gap: '15px' }}>
                    <button onClick={() => navigate('/students')} className="btn-primary" style={{ width: 'auto' }}>
                        Manage Students
                    </button>
                    <button onClick={handleLogout} className="btn-danger">
                        Logout
                    </button>
                </div>
            </div>
            
            {stats ? (
                <div className="stats-grid">
                    <div className="stat-card glass-panel">
                        <h3 className="stat-title">Total Students</h3>
                        <p className="stat-value">{stats.totalStudents}</p>
                    </div>
                    <div className="stat-card glass-panel" style={{ '--primary-accent': '#10b981' }}>
                        <h3 className="stat-title">Active Placements</h3>
                        <p className="stat-value">{stats.activePlacements}</p>
                    </div>
                    <div className="stat-card glass-panel" style={{ '--primary-accent': '#8b5cf6' }}>
                        <h3 className="stat-title">Upcoming Events</h3>
                        <p className="stat-value">{stats.upcomingEvents}</p>
                    </div>
                    <div className="stat-card glass-panel" style={{ '--primary-accent': '#f59e0b' }}>
                        <h3 className="stat-title">Average CGPA</h3>
                        <p className="stat-value">{stats.averageCgpa}</p>
                    </div>
                </div>
            ) : (
                <div style={{ textAlign: 'center', marginTop: '100px', color: 'var(--text-secondary)' }}>
                    <h2>Loading Statistics...</h2>
                </div>
            )}
        </div>
    );
};

export default Dashboard;
