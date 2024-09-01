import React from 'react';

const FooterComponent = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className='bg-gray-800 text-white py-4 fixed bottom-0 left-0 right-0'>
      <div className='container mx-auto px-4'>
        <p className='text-center text-sm'>
          Copyrights reserved at {currentYear} by Estelle Academy
        </p>
      </div>
    </footer>
  );
};

export default FooterComponent;
