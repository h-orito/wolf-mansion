import Breadcrumb from '@/components/ui/Breadcrumb'
import { faHistory } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Announce2018 from './components/2018'
import Announce2019 from './components/2019'
import Announce2020 from './components/2020'
import Announce2021 from './components/2021'
import Announce2022 from './components/2022'
import Announce2023 from './components/2023'
import Announce2024 from './components/2024'
import Announce2025 from './components/2025'

export default function AnnouncePage() {
  return (
    <div className='mt-8'>
      <Breadcrumb items={[{ label: 'リリースノート' }]} />
      <div className='content-section'>
        <h1 className='section-title'>
          <FontAwesomeIcon icon={faHistory} className='text-primary h-5 w-5' />
          リリースノート
        </h1>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2025年
          </h2>
          <ul className='list-content'>
            <Announce2025 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2024年
          </h2>
          <ul className='list-content'>
            <Announce2024 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2023年
          </h2>
          <ul className='list-content'>
            <Announce2023 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2022年
          </h2>
          <ul className='list-content'>
            <Announce2022 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2021年
          </h2>
          <ul className='list-content'>
            <Announce2021 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2020年
          </h2>
          <ul className='list-content'>
            <Announce2020 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2019年
          </h2>
          <ul className='list-content'>
            <Announce2019 />
          </ul>
        </div>

        <div className='mb-8'>
          <h2 className='mb-4 border-b border-gray-700 pb-2 text-xl font-bold'>
            2018年
          </h2>
          <ul className='list-content'>
            <Announce2018 />
          </ul>
        </div>
      </div>
    </div>
  )
}
