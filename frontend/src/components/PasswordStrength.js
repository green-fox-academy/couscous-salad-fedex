import React from 'react';
import zxcvbn from 'zxcvbn';

import './PasswordStrength.css';

const PasswordStrength = ({ password }) => {
  const testResult = zxcvbn(password);
  const num = (testResult.score * 100) / 4;

  const createPasswordLabel = () => {
    switch (testResult.score) {
      case 0:
        return 'Very weak';
      case 1:
        return 'Weak';
      case 2:
        return 'Adequate';
      case 3:
        return 'Good';
      case 4:
        return 'Strong';
      default:
        return 'none';
    }
  };

  const progressColor = () => {
    switch (testResult.score) {
      case 0:
        return '#616060';
      case 1:
        return '#b90606';
      case 2:
        return '#d89400d3';
      case 3:
        return '#a9f526';
      case 4:
        return '#67a835';
      default:
        return 'none';
    }
  };

  const changePasswordColor = () => ({
    width: `${num}%`,
    background: progressColor(),
  });

  return (
    <>
      <div className="progress">
        <div className="progress-bar" style={changePasswordColor()} />
      </div>
      <p style={{ color: progressColor() }}>{createPasswordLabel()}</p>
    </>
  );
};

export default PasswordStrength;
