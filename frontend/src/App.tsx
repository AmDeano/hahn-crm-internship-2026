import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Projects from './pages/Projects';
import ProjectDetail from './pages/ProjectDetail';
import ProtectedRoute from './components/ProtectedRoute';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route
                    path="/"
                    element={
                        <ProtectedRoute>
                            <Projects />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/projects/:projectId"
                    element={
                        <ProtectedRoute>
                            <ProjectDetail />
                        </ProtectedRoute>
                    }
                />
                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </Router>
    );
};

export default App;
