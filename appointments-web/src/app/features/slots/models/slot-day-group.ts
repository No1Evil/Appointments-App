import {SlotResponse} from "../../../core/api";

export interface SlotDayGroup {
  date: Date;
  label: string;
  slots: SlotResponse[];
}
