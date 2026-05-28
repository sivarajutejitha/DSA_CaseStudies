import java.util.Arrays;
class Meeting {
    String name;
    int start;
    int end;
    Meeting(String name, int start, int end) {
        this.name  = name;
        this.start = start;
        this.end   = end;
    }
}
public class ConferenceScheduler {
    // ── ACTIVITY SELECTION (GREEDY) ─────────────────────────
    static void selectMeetings(Meeting[] meetings) {
        int n = meetings.length;
        // Step 1: Sort by finish time
        Arrays.sort(meetings, (a, b) -> a.end - b.end);
        System.out.println("\n=== Applying Greedy Activity Selection ===");
        Meeting[] selected = new Meeting[n];
        int count          = 0;
        int lastEndTime    = 0; // tracks end time of last selected meeting
        for (Meeting m : meetings) {
            if (m.start >= lastEndTime) {
                // No overlap — select this meeting
                selected[count++] = m;
                lastEndTime       = m.end;
                System.out.printf("  -> Selected  : %-20s | %02d:00 - %02d:00%n",
                        m.name, m.start, m.end);
            } else {
                // Overlap — skip this meeting
                System.out.printf("  -> Skipped   : %-20s | %02d:00 - %02d:00" +
                        "  (overlaps)%n", m.name, m.start, m.end);
            }
        }
        // Print final schedule
        System.out.println("\n=== Conference Room Schedule for the Day ===");
        for (int i = 0; i < count; i++)
            System.out.printf("  Slot %d : %-20s | %02d:00 - %02d:00%n",
                    i + 1, selected[i].name,
                    selected[i].start, selected[i].end);
        System.out.printf("%n  Total Meetings Scheduled : %d out of %d requests%n",
                count, n);
    }
    // ── MAIN ────────────────────────────────────────────────
    public static void main(String[] args) {
        Meeting[] meetings = {
            new Meeting("Team Standup",      9,  10),
            new Meeting("Project Review",    9,  11),
            new Meeting("Client Call",       10, 12),
            new Meeting("HR Induction",      11, 12),
            new Meeting("Budget Discussion", 12, 13),
            new Meeting("Training Session",  12, 14),
            new Meeting("Design Review",     13, 15),
            new Meeting("Sprint Planning",   14, 16)
        };
        // Print all requests
        System.out.println("=== Meeting Requests for the Day ===");
        for (int i = 0; i < meetings.length; i++)
            System.out.printf("  Meeting %d : %-20s | %02d:00 - %02d:00%n",
                    i + 1, meetings[i].name,
                    meetings[i].start, meetings[i].end);
        // Run greedy selection
        selectMeetings(meetings);
    }
}