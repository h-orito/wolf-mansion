'use client'

import { faGithub } from '@fortawesome/free-brands-svg-icons'
import {
  faEnvelope,
  faFileLines,
  faHeart
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useState } from 'react'
import DonationModal from './DonationModal'
import PrivacyPolicyModal from './PrivacyPolicyModal'

const Footer = () => {
  const [isDonationModalOpen, setIsDonationModalOpen] = useState(false)
  const [isPrivacyPolicyModalOpen, setIsPrivacyPolicyModalOpen] =
    useState(false)
  return (
    <footer className='mt-8 w-full border-t border-gray-700 py-6'>
      <div className='container mx-auto px-4'>
        <div className='flex flex-col items-center justify-between md:flex-row'>
          <div className='mb-4 md:mb-0'>
            <p className='text-sm text-gray-400'>© 2018- ort</p>
          </div>
          <div className='flex flex-wrap justify-center gap-4'>
            <a
              href='https://twitter.com/ort_dev'
              target='_blank'
              rel='noopener noreferrer'
              className='flex items-center gap-2 text-gray-400 transition-colors hover:text-white'
              title='お問い合わせ'
            >
              <FontAwesomeIcon icon={faEnvelope} />
              <span className='text-sm'>お問い合わせ</span>
            </a>
            <button
              onClick={() => setIsDonationModalOpen(true)}
              className='flex items-center gap-2 text-gray-400 transition-colors hover:text-white'
              title='投げ銭'
            >
              <FontAwesomeIcon icon={faHeart} />
              <span className='text-sm'>投げ銭</span>
            </button>
            <DonationModal
              isOpen={isDonationModalOpen}
              onClose={() => setIsDonationModalOpen(false)}
            />
            <button
              onClick={() => setIsPrivacyPolicyModalOpen(true)}
              className='flex items-center gap-2 text-gray-400 transition-colors hover:text-white'
              title='プライバシーポリシー'
            >
              <FontAwesomeIcon icon={faFileLines} />
              <span className='text-sm'>プライバシーポリシー</span>
            </button>
            <PrivacyPolicyModal
              isOpen={isPrivacyPolicyModalOpen}
              onClose={() => setIsPrivacyPolicyModalOpen(false)}
            />
            <a
              href='https://github.com/h-orito/wolf-mansion'
              target='_blank'
              rel='noopener noreferrer'
              className='flex items-center gap-2 text-gray-400 transition-colors hover:text-white'
              title='GitHub'
            >
              <FontAwesomeIcon icon={faGithub} />
              <span className='text-sm'>GitHub</span>
            </a>
          </div>
        </div>
      </div>
    </footer>
  )
}

export default Footer
