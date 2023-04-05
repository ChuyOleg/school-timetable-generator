//package ip91.chui.oleh.model;
//
//import ip91.chui.oleh.model.entity.Group;
//import ip91.chui.oleh.model.entity.GroupLimits;
//
//public class SimpleFitnessFunction {
//
//  private static final int FAIL_SUBJECT_COUNT_FINE = 100;
//
//  public int process(Group group, GroupLimits groupLimits) {
//    return checkByGroupLimits(group, groupLimits);
//  }
//
//  private int checkByGroupLimits(Group group, GroupLimits groupLimits) {
//    return groupLimits.getSubjectHoursInGroupSet().stream()
//        .reduce(0, (acc, entity) -> {
//          long count = group.getLessonSet().stream().filter(lesson -> lesson.getSubject().equals(entity.getSubject())).count();
//          return acc + FAIL_SUBJECT_COUNT_FINE * (int) Math.abs(entity.getHours() - count);
//        }, Integer::sum);
//  }
//
//}
