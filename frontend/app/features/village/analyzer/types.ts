export type PlayerMemo = {
  dailyMemos: DailyMemo[];
  dailyFootstepMemos: DailyFootstepMemo[];
  wholeMemo: string;
  participantMemos: ParticipantMemo[];
};

export type DailyMemo = {
  day: number;
  memo: string;
};

export type DailyFootstepMemo = {
  day: number;
  footsteps: DayFootstep[];
};

export type DayFootstep = {
  footstep: string;
  color: string;
  show: boolean;
  memo: string;
};

export type ParticipantMemo = {
  participantId: number;
  memo: string;
  color: string;
};
