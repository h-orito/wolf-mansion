'use client'

import { Player } from '@/lib/openapi-typescript/wolf-mansion/types'
import Link from 'next/link'

type Props = {
  players: Player[]
}

export default function UserListTable({ players }: Props) {
  return (
    <div className='overflow-x-auto'>
      <table className='table'>
        <thead>
          <tr className=''>
            <th className='th text-left'>ユーザー名</th>
          </tr>
        </thead>
        <tbody>
          {players.map((player) => (
            <tr key={player.name} className='border border-[#444444]'>
              <td className='td'>
                <Link
                  href={`/user/${player.name}`}
                  className='link-style'
                  rel='noopener noreferrer'
                >
                  {player.name}
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
