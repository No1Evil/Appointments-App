import { Component, Inject, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {
  AppointmentResponse,
  AppointmentService,
  UpdateAppointmentCommentRequest
} from '../../api';

@Component({
  selector: 'comment-dialog',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule
  ],
  templateUrl: 'comment-dialog.component.html',
  styleUrl: 'comment-dialog.component.scss'
})
export class CommentDialogComponent implements OnInit {

  public comment = '';
  public submitting = false;
  public error?: string;

  constructor(
    public dialogRef: MatDialogRef<CommentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public appointment: AppointmentResponse,
    private appointmentService: AppointmentService
  ) {}

  public ngOnInit(): void {
    this.comment = this.appointment.comment ?? '';
  }

  public submit(): void {
    this.submitting = true;
    this.error = undefined;

    const request: UpdateAppointmentCommentRequest = {
      appointmentId: this.appointment.id,
      comment: this.comment
    };

    this.appointmentService.updateAppointmentComment(request).subscribe({
      next: () => this.dialogRef.close(true),
      error: (err) => {
        this.submitting = false;
        this.error = err?.error?.message ?? 'Failed to update comment';
      }
    });
  }

}
