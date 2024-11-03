export class CommonUtils {

  static async artificialPause(durationMillis: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, durationMillis));
  }
}
