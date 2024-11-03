import { TranslateDayPipe } from './translate-day.pipe';
import { EDayOfWeek } from "../models/enumeration/day-of-week";

describe('TranslateDayPipe', () => {
  let pipe: TranslateDayPipe;

  beforeEach(() => {
    pipe = new TranslateDayPipe();
  });

  it('should translate MONDAY to "Понеділок"', () => {
    expect(pipe.transform(EDayOfWeek.MONDAY)).toBe('Понеділок');
  });

  it('should translate TUESDAY to "Вівторок"', () => {
    expect(pipe.transform(EDayOfWeek.TUESDAY)).toBe('Вівторок');
  });

  it('should translate WEDNESDAY to "Середа"', () => {
    expect(pipe.transform(EDayOfWeek.WEDNESDAY)).toBe('Середа');
  });

  it('should translate THURSDAY to "Четвер"', () => {
    expect(pipe.transform(EDayOfWeek.THURSDAY)).toBe('Четвер');
  });

  it('should translate FRIDAY to "П\'ятниця"', () => {
    expect(pipe.transform(EDayOfWeek.FRIDAY)).toBe('П\'ятниця');
  });

  it('should return empty string for an invalid day', () => {
    expect(pipe.transform(undefined as unknown as EDayOfWeek)).toBe('');
  });
});
